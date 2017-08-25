import {Component, OnInit} from '@angular/core';
import {EventBusService} from "../EventBusService";

@Component({
  selector: 'app-matrix',
  templateUrl: './matrix.component.html',
  styleUrls: ['./matrix.component.css']
})
export class MatrixComponent implements OnInit {

  size = 1000
  all = []

  constructor() {
    for (let i = 1; i <= this.size; i++) {
      this.all.push({})
    }

    let eb = new EventBusService()
    eb.connect("/eventbus")
    eb.open.subscribe(x => {

      eb.registerHandler("test", (error, message) => {
        console.log(message)
      })

    })


  }

  ngOnInit() {


  }

}
