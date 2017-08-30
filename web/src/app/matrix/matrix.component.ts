import {Component, OnInit} from '@angular/core';
import {EventBusService} from "../EventBusService";
import {BenchService} from "../bench.service";

@Component({
  selector: 'app-matrix',
  templateUrl: './matrix.component.html',
  providers: [BenchService],
  styleUrls: ['./matrix.component.css']
})
export class MatrixComponent implements OnInit {

  size = 1000
  all = []

  constructor(public service: BenchService) {
    for (let i = 1; i <= this.size; i++) {
      this.all.push({})
    }

    let eb = new EventBusService()
    eb.connect("/eventbus")
    eb.open.subscribe(x => {

      eb.registerHandler("com.aruistar.bench.1", (error, message) => {
        console.log(error)
        console.log(message)
      })

    })


  }

  ngOnInit() {


  }

}
