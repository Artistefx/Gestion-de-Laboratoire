import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ message }}</h1>
    <button (click)="updateMessage()">Update Message</button>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  message: string = 'Hello, World!';

  updateMessage() {
    this.message = 'Hello, Jest!';
  }
}
