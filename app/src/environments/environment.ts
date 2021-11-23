// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  serverUrl: 'http://localhost:8080',

  googleAuthUrl: 'https://accounts.google.com/o/oauth2/v2/auth',
  googleClientId: '932356479055-koogrkg18qg06ccbp02ngbp5fkd4k0cc.apps.googleusercontent.com',

  energyMin: 0,
  energyMax: 1000,
  energyDivider: 10,
  maturityMin: 0,
  maturityMax: 10000,
  maturityDivider: 100,
  statsMin: 0,
  statsMax: 100,
  statsLoveRequirement: 75,
  breedingMax: 3,

  firebase: {
    apiKey: 'AIzaSyBrq7lRwYNpFlGtce0PTTRvgz14JvS2WlQ',
    authDomain: 'llamabreeder.firebaseapp.com',
    databaseURL: '<your-database-URL>',
    projectId: 'llamabreeder',
    storageBucket: '<your-storage-bucket>',
    messagingSenderId: '<your-messaging-sender-id>',
    appId: '<your-app-id>',
    measurementId: '<your-measurement-id>'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
