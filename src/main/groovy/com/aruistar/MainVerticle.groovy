package com.aruistar

import com.aruistar.bench.BenchVerticle
import com.aruistar.http.HttpVerticle
import com.aruistar.other.AruisLog
import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Future

class MainVerticle extends AbstractVerticle implements AruisLog {

    @Override
    void start(Future<Void> startFuture) throws Exception {

        log.info("config:" + config().toString())

        vertx.deployVerticle(BenchVerticle.newInstance(), { dbHandler ->
            if (dbHandler.succeeded()) {

                vertx.deployVerticle(HttpVerticle.newInstance(), new DeploymentOptions().setConfig(config()), { httpHandler ->
                    if (httpHandler.succeeded()) {
                        startFuture.complete()
                    }

                })


            }

        })


    }
}
