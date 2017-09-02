package com.aruistar.bench

import com.aruistar.entity.BenchForm
import com.aruistar.other.AruisLog
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

class BenchServiceImpl implements BenchService, AruisLog {

    Vertx vertx

    BenchServiceImpl(Vertx vertx, JsonObject config, Handler<AsyncResult<BenchService>> readyHandler) {

        this.vertx = vertx
        readyHandler.handle(Future.succeededFuture(this));

    }

    @Override
    BenchService bench(BenchForm form, Handler<AsyncResult<String>> resultHandler) {

        log.info("bench begin " + form.toString())

        WebClient client = WebClient.create(vertx, new WebClientOptions().setKeepAlive(form.keepAlive).setMaxPoolSize(form.maxPoolSize).setConnectTimeout(form.timeout))

        resultHandler.handle(Future.succeededFuture(form.uuid))

        int all = form.allRequestTimes
        int fail = 0
        int suc = 0

        def start = new Date()
        log.info(start.toString())

        form.url = form.url.replace("http://", "")


        def peri
        peri = vertx.setPeriodic(1000, {
            vertx.eventBus().send("com.aruistar.bench.${form.uuid}", suc)
            if (all == 0) {
                vertx.cancelTimer(peri)
            }
        })

        all.times {
            client.getAbs("http://" + form.url).send({ ar ->

                if (ar.succeeded()) {
                    suc++
//                    log.info(ar.result().bodyAsString())
                } else {
                    fail++
                }

                if (--all == 0) {
                    def expend = (new Date().time - start.time) / 1000
                    log.info("expend $expend s ,suc num is $suc, fail num is $fail")
                }


            })


        }


        return this
    }
}
