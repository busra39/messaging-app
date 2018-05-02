
import { Component } from '@angular/core';
import { MessageService } from './message.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  chart = [];

  constructor(private _message: MessageService) {}
  ngOnInit() {
    this._message.messages()
      .subscribe(res => {

        let delivered = res.MODelivered
        let received = res.MOReceived
        let charged = res.MOCharged
        let terminated =  res.MOTerminated
        let report =  res.MOReport

        this.chart = new Chart('canvas', {
          type: 'line',
          data: {
            datasets: [{
              data: [delivered, received, charged, terminated, report],
              borderColor: "#f23c00",
            }],

          },options: {
            scales: {
              xAxes: [{
                type: 'category',
                labels: ['MODelivered', 'MOReceived', 'MOCharged', 'MOTerminated', 'MOReport'],
              }]
            }
          }
        })


      })
  }
}

