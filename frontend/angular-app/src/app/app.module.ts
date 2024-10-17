import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    // other components...
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule, // This should be included
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
