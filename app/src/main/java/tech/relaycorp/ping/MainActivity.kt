package tech.relaycorp.ping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.relaycorp.relaydroid.RelaynetTemp
import tech.relaycorp.relaydroid.endpoint.FirstPartyEndpoint
import tech.relaycorp.relaydroid.endpoint.PublicThirdPartyEndpoint
import tech.relaycorp.relaydroid.messaging.MessageId
import tech.relaycorp.relaydroid.messaging.OutgoingMessage
import tech.relaycorp.relaynet.wrappers.x509.Certificate
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    private val component by lazy { (applicationContext as App).component }

    private val backgroundContext = lifecycleScope.coroutineContext + Dispatchers.IO
    private val backgroundScope = CoroutineScope(backgroundContext)
    lateinit var sender: FirstPartyEndpoint
    lateinit var recipient: PublicThirdPartyEndpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            send.isEnabled = false
            withContext(backgroundContext) {
                RelaynetTemp.GatewayClient.bind()
                sender = FirstPartyEndpoint.register()
                recipient = PublicThirdPartyEndpoint.import(
                    Certificate.deserialize(
                        resources.openRawResource(R.raw.identity).use { it.readBytes() }
                    )
                )
            }
            send.isEnabled = true
        }

        repository
            .observe()
            .onEach {
                pings.text = it.joinToString("\n") { message ->
                    "Ping (sent=${Date(message.sent)}) (received=${
                        message.received?.let {
                            Date(
                                message.received
                            )
                        }
                    })"
                }
            }
            .launchIn(lifecycleScope)

        send.setOnClickListener {
            backgroundScope.launch {
                val id = MessageId.generate()
                val authorization = sender.issueAuthorization(
                    recipient.certificate.subjectPublicKey,
                    ZonedDateTime.now().plusDays(3)
                )
                val pingSerialized = serializePing(
                    id.value,
                    authorization.pdaSerialized,
                    authorization.pdaChainSerialized
                )
                val outgoingMessage = OutgoingMessage.build(
                    payload = pingSerialized,
                    senderEndpoint = sender,
                    recipientEndpoint = recipient,
                    id = id
                )
                RelaynetTemp.GatewayClient.sendMessage(outgoingMessage)
                val pingMessage = PingMessage(outgoingMessage.id.value)
                repository.set(pingMessage)
            }
        }

        clear.setOnClickListener {
            backgroundScope.launch {
                repository.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RelaynetTemp.GatewayClient.unbind()
    }
}
