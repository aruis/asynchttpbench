///<reference path="../bench.service.ts"/>
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {BenchService, MetaBench} from "../bench.service";
import * as uuid from 'uuid';

@Component({
  selector: 'app-my-form',
  templateUrl: './my-form.component.html',
  providers: [BenchService],
  styleUrls: ['./my-form.component.css']
})
export class MyFormComponent implements OnInit {

  validateForm: FormGroup;

  countIndex() {
    for (let i = 1; i <= 100; i++) {
      this.http.get("/count?index=" + i).subscribe(data => {
        console.log(data)
      })
    }
  }

  _submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty()
      // return
    }

    let value = this.validateForm.value


    value.maxPoolSize = parseInt(value.maxPoolSize)
    value.timeout = parseInt(value.timeout)
    value.allRequestTimes = parseInt(value.allRequestTimes)
    value.uuid = uuid.v4()

    this.service.event.emit(new MetaBench(value.uuid, value.allRequestTimes))
    this.http.post("/bench", this.validateForm.value).subscribe(data => {
      console.log(data)
    })

  }

  constructor(private fb: FormBuilder, private http: HttpClient, private service: BenchService) {
  }


  ngOnInit() {
    this.validateForm = this.fb.group({
      url: ["http://127.0.0.1:7070/", [Validators.required]],
      keepAlive: [true],
      maxPoolSize: [5],
      timeout: [5],
      allRequestTimes: [10],
    });
  }

  getFormControl(name) {
    return this.validateForm.controls[name];
  }
}
