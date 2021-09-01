import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Cookie} from "ng2-cookies";
import {ActivatedRoute} from "@angular/router";

export class AccessToken {
  constructor(public token: string,
              public expiresIn: number) {
  }
}

@Injectable()
export class AuthService {
  private authUrl = 'https://accounts.google.com/o/oauth2/v2/auth';
  private clientId = '932356479055-koogrkg18qg06ccbp02ngbp5fkd4k0cc.apps.googleusercontent.com';

  constructor(private _http: HttpClient,
              private route: ActivatedRoute) {
  }

  login() {
    let redirectUri = window.location.href;
    // console.log(redirectUri);
    // let split = redirectUri.split('/');
    // redirectUri = split[0] + '//' + split[1] + split[2];
    if (redirectUri.charAt(redirectUri.length-1) == '/')
      redirectUri = redirectUri.slice(0, redirectUri.length - 1);
    // console.log(redirectUri);
    window.location.href =
      this.authUrl +
      '?response_type=token&scope=openid%20profile%20email' +
      '&client_id=' + this.clientId +
      '&redirect_uri=' + redirectUri;
  }

  retrieveToken(url: string ) {
    console.log(url);
    let split = url.split("#");
    let fragments = split[1].split('&');
    let accessToken = new AccessToken(
      fragments.find(fragment => fragment.startsWith("access_token")).slice("access_token".length + 1),
      Number(fragments.find(fragment => fragment.startsWith("expires_in")).slice("expires_in".length + 1))
    );
    this.saveToken(accessToken);
  }

  saveToken(accessToken: AccessToken) {
    let dtExpires = new Date(new Date().getTime() + Number(accessToken.expiresIn) * 1000);
    Cookie.set("access_token", accessToken.token, dtExpires);
    window.location.href = window.location.href.split('#')[0];
  }

  isLoggedIn() {
    return Cookie.check('access_token');
  }

  logout() {
    Cookie.delete('access_token');
    window.location.href = '';
  }
}
