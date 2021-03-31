package tech.relaycorp.ping.ui.peers

import android.net.Uri
import kotlinx.coroutines.flow.*
import tech.relaycorp.gateway.ui.BaseViewModel
import tech.relaycorp.ping.common.PublishFlow
import tech.relaycorp.ping.data.ReadFile
import tech.relaycorp.ping.domain.AddPublicPeer
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

    private val alias = MutableStateFlow("")
    fun aliasChanged(value: String) {
        alias.value = value
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

    private val _hasCertificate = MutableStateFlow(false)
    fun hasCertificate(): Flow<Boolean> = _hasCertificate.asStateFlow()

    private val _saveEnabled = MutableStateFlow(false)
    fun saveEnabled(): Flow<Boolean> = _saveEnabled.asStateFlow()

    private val _errors = PublishFlow<Error>()
    fun errors(): Flow<Error> = _errors.asFlow()

    private val _finish = PublishFlow<Finish>()
    fun finish(): Flow<Finish> = _finish.asFlow()

    init {
        val form = alias.combine(certificatePicks, ::Form)

        form
            .onEach { _saveEnabled.value = it.isValid() }
            .launchIn(backgroundScope)

        certificatePicks
            .onEach { _hasCertificate.value = it.isPresent }
            .launchIn(backgroundScope)

        removeCertificateClicks
            .asFlow()
            .onEach {
                certificatePicks.value = Optional.empty()
                _hasCertificate.value = false
            }
            .launchIn(backgroundScope)

        saveClicks
            .asFlow()
            .flatMapLatest { form.take(1) }
            .filter { it.isValid() }
            .onEach { f ->
                val cert = readFile.read(f.certificateUri.get())
                addPublicPeer.add(f.alias, cert)
                _finish.finish()
            }
            .catch {
                _errors.send(Error.Save)
            }
            .launchIn(backgroundScope)
    }

    enum class Error {
        Save
    }

    data class Form(
        val alias: String,
        val certificateUri: Optional<Uri>
    ) {
        fun isValid() =
            alias.isNotBlank()
                    && alias.matches(Regex("(\\w+\\.)+\\w+"))
                    && certificateUri.isPresent
    }
}
