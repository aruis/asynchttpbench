import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {BenchService} from "../bench.service";


@Component({
  selector: 'app-my-form',
  templateUrl: './my-form.component.html',
  providers: [BenchService],
  styleUrls: ['./my-form.component.css']
})
export class MyFormComponent implements OnInit {

  validateForm: FormGroup;

  _submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty()
      // return
    }

    var value = this.validateForm.value


    value.maxPoolSize = parseInt(value.maxPoolSize)
    value.timeout = parseInt(value.timeout)
    value.allRequestTimes = parseInt(value.allRequestTimes)

    this.http.post("/bench", this.validateForm.value).subscribe(data => {
      this.service.uuid = data["uuid"]
    })


    console.log(this.validateForm.value)
  }

  constructor(private fb: FormBuilder, private http: HttpClient, private service: BenchService) {
  }


  ngOnInit() {
    this.validateForm = this.fb.group({
      url: [null, [Validators.required]],
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
