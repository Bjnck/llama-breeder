import {Injectable} from '@angular/core';
import {Cookie} from 'ng2-cookies';
import {environment} from '../../../environments/environment';

export class AccessToken {
  constructor(public token: string,
              public expiresIn: number) {
  }
}

@Injectable()
export class AuthService {
  private authUrl = environment.googleAuthUrl;
  private clientId = environment.googleClientId;

  login() {
    let redirectUri = window.location.href;
    if (redirectUri.charAt(redirectUri.length - 1) === '/') {
      redirectUri = redirectUri.slice(0, redirectUri.length - 1);
    }
    window.location.href =
      this.authUrl +
      '?response_type=token&scope=openid%20profile%20email' +
      '&client_id=' + this.clientId +
      '&redirect_uri=' + redirectUri;
  // &state=http://example.com/last-page-the-user-loaded
  }

  retrieveToken(url: string) {
    const split = url.split('#');
    const fragments = split[1].split('&');
    const accessToken = new AccessToken(
      fragments.find(fragment => fragment.startsWith('access_token')).slice('access_token'.length + 1),
      Number(fragments.find(fragment => fragment.startsWith('expires_in')).slice('expires_in'.length + 1))
    );
    this.saveToken(accessToken);
  }

  saveToken(accessToken: AccessToken) {
    const dtExpires = new Date(new Date().getTime() + Number(accessToken.expiresIn) * 1000);
    Cookie.set('access_token', accessToken.token, dtExpires);
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
