import {Component, OnInit} from '@angular/core';
import {EventBusService} from "../EventBusService";
import {BenchService, MetaBench} from "../bench.service";

@Component({
  selector: 'app-matrix',
  templateUrl: './matrix.component.html',
  providers: [BenchService],
  styleUrls: ['./matrix.component.css']
})
export class MatrixComponent implements OnInit {

  uuid
  all = []

  constructor(public service: BenchService) {


    let eb = new EventBusService()
    eb.connect("/eventbus", null, {"vertxbus_ping_interval": 100})
    eb.open.subscribe(x => {


    })

    this.service.event.subscribe((item: MetaBench) => {
      this.uuid = item.uuid

      this.all = []

      let size = item.times


      eb.registerHandler("com.aruistar.bench." + item.uuid, (error, message) => {

        setTimeout(x => {
          let suc = message['body']
          for (let i = 0; i < suc; i++) {
            this.all[i].ok = 1
          }
        })


      })

      for (let i = 1; i <= size; i++) {
        this.all.push({ok: 0})
      }

      console.log(this.uuid)
    })

  }


  ngOnInit() {


  }

}
