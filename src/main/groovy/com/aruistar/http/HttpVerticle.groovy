package com.aruistar.http

import com.aruistar.bench.BenchService
import com.aruistar.entity.BenchForm
import com.aruistar.entity.BenchFormConverter
import com.aruistar.other.AruisLog
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.BridgeEventType
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.PermittedOptions
import io.vertx.ext.web.handler.sockjs.SockJSHandler

class HttpVerticle extends AbstractVerticle implements AruisLog {

    BenchService service

    @Override
    void start(Future<Void> startFuture) throws Exception {
        service = BenchService.createProxy(vertx, "aruistar.bench")

        int port = config().getInteger("port", 8080)

        HttpServer server = vertx.createHttpServer()

        Router router = Router.router(vertx)



        router.post().handler(BodyHandler.create())

        router.post("/bench").handler({ context ->

            JsonObject body = context.getBodyAsJson()
            BenchForm form = new BenchForm()
            BenchFormConverter.fromJson(body, form)
            service.bench(form, {

            })

            context.response().putHeader('content-type', 'application/json').end(body.toString())
        })


        BridgeOptions options = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("com.aruistar.bench"))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex(/com\.aruistar\.bench\..+/))

        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options, { event ->

            // You can also optionally provide a handler like this which will be passed any events that occur on the bridge
            // You can use this for monitoring or logging, or to change the raw messages in-flight.
            // It can also be used for fine grained access control.
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("A socket was created");
            }

            // This signals that it's ok to process the event
            event.complete(true)

        }));


        router.route().handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot("/Users/liurui/develop/workspace-study/asynchttpbench/web/dist"))

        vertx.setPeriodic(2000, {
            vertx.eventBus().send("com.aruistar.bench.1", "hello")

        })

        server.requestHandler(router.&accept)
                .listen(port, { ar ->
            if (ar.succeeded()) {
                log.info("HTTP server running on port " + port)
                startFuture.complete()
            } else {
                log.error("Could not start a HTTP server", ar.cause())
                startFuture.fail(ar.cause())
            }

        })
    }
}
