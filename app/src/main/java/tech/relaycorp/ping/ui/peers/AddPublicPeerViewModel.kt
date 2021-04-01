package tech.relaycorp.ping.ui.peers

import android.net.Uri
import kotlinx.coroutines.flow.*
import tech.relaycorp.gateway.ui.BaseViewModel
import tech.relaycorp.ping.common.PublishFlow
import tech.relaycorp.ping.common.element
import tech.relaycorp.ping.data.ReadFile
import tech.relaycorp.ping.domain.AddPublicPeer
import tech.relaycorp.ping.domain.InvalidIdentityCertificate
import tech.relaycorp.ping.ui.common.Click
import tech.relaycorp.ping.ui.common.Finish
import tech.relaycorp.ping.ui.common.clicked
import tech.relaycorp.ping.ui.common.finish
import java.util.*
import javax.inject.Inject

class AddPublicPeerViewModel
@Inject constructor(
    addPublicPeer: AddPublicPeer,
    readFile: ReadFile
) : BaseViewModel() {

    // Inputs

    private val address = MutableStateFlow("")
    fun aliasChanged(value: String) {
        address.value = value
    }

    private val certificatePicks = MutableStateFlow(Optional.empty<Uri>())
    fun certificatePicked(value: Uri) {
        certificatePicks.value = Optional.of(value)
    }

    private val removeCertificateClicks = PublishFlow<Click>()
    fun removeCertificateClicked() {
        removeCertificateClicks.clicked()
    }

    private val saveClicks = PublishFlow<Click>()
    fun saveClicked() {
        saveClicks.clicked()
    }

    // Outputs

    private val _certificate = MutableStateFlow(Optional.empty<String>())
    fun certificate(): Flow<Optional<String>> = _certificate.asStateFlow()

    private val _errors = PublishFlow<Error>()
    fun errors(): Flow<Error> = _errors.asFlow()

    private val _finish = PublishFlow<Finish>()
    fun finish(): Flow<Finish> = _finish.asFlow()

    init {
        val form = address.combine(certificatePicks, ::Form)

        certificatePicks
            .element()
            .onEach { _certificate.value = Optional.of(readFile.getFileName(it)) }
            .launchIn(backgroundScope)

        removeCertificateClicks
            .asFlow()
            .onEach {
                certificatePicks.value = Optional.empty()
                _certificate.value = Optional.empty()
            }
            .launchIn(backgroundScope)

        saveClicks
            .asFlow()
            .flatMapLatest { form.take(1) }
            .map { f ->
                if (f.isValid()) {
                    val cert = readFile.read(f.certificateUri.get())
                    addPublicPeer.add(f.address, cert)
                    _finish.finish()
                } else {
                    _errors.send(
                        when {
                            !f.isAddressValid() -> Error.InvalidAddress
                            !f.isCertificatePresent() -> Error.MissingCertificate
                            else -> Error.GenericSave
                        }
                    )
                }
            }
            .catch { exception ->
                _errors.send(
                    when (exception) {
                        is InvalidIdentityCertificate -> Error.InvalidCertificate
                        else -> Error.GenericSave
                    }
                )
                emit(Unit)
            }
            .launchIn(backgroundScope)
    }

    enum class Error {
        InvalidAddress, MissingCertificate, InvalidCertificate, GenericSave
    }

    data class Form(
        val address: String,
        val certificateUri: Optional<Uri>
    ) {
        fun isAddressValid() =
            address.isNotBlank() && address.matches(PUBLIC_ADDRESS_REGEX)

        fun isCertificatePresent() = certificateUri.isPresent

        fun isValid() = isAddressValid() && isCertificatePresent()
    }

    companion object {
        internal val PUBLIC_ADDRESS_REGEX =
            Regex("([a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9]\\.)+[a-zA-Z]{2,}")
    }
}
