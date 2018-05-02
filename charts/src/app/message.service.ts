import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';


@Injectable()
export class MessageService {

  constructor(private _http: HttpClient) { }

  messages() {
    return this._http.get("http://localhost:8081/list")
      .map(result => result);
  }
}
