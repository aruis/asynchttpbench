import {EventEmitter, Injectable} from '@angular/core';

const EVENT = new EventEmitter();

@Injectable()
export class BenchService {

  event = EVENT

  // uuid: string = "hello world"

  constructor() {
  }

}

export class MetaBench {
  uuid: string
  times: number

  constructor(uuid: string, times: number) {
    this.uuid = uuid
    this.times = times
  }
}
