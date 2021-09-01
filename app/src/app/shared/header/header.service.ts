import {EventEmitter, Injectable, Output} from "@angular/core";
import {Header} from "./header.interface";

@Injectable()
export class HeaderService {

  @Output() headerChangeEventEmitter: EventEmitter<Header> = new EventEmitter<Header>();

  private _header: Header = new class implements Header {
    display: boolean = true;
    isHome: boolean;
    title: String;
  };

  showHeader(title: string, isHome: boolean) {
    this._header.display = true;
    this._header.title = title;
    this._header.isHome = isHome;
    this.headerChangeEventEmitter.emit(this._header);
  }

  hideHeader() {
    this._header.display = false;
    this.headerChangeEventEmitter.emit(this._header);
  }
}
