package com.aruistar.http

import com.aruistar.bench.BenchService
import com.aruistar.other.AruisLog
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler

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

            JsonObject json = context.getBodyAsJson()
            println(json)

            context.response().putHeader('content-type', 'application/json').end(json.toString())
        })

        router.route().handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot("/Users/liurui/develop/workspace-study/asynchttpbench/web/dist"))


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
