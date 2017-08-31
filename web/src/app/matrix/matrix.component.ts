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
    eb.connect("/eventbus")
    eb.open.subscribe(x => {


    })

    this.service.event.subscribe((item: MetaBench) => {
      this.uuid = item.uuid

      this.all = []

      let size = item.times

      let i = 0


      eb.registerHandler("com.aruistar.bench." + item.uuid, (error, message) => {

        setTimeout(x => {
          if (message['body']) {
            this.all[i].ok = 1
          } else {
            this.all[i].ok = -1
          }
          i++
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
