// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  serverUrl: 'http://localhost:8080',

  energyMin: 0,
  energyMax: 1000,
  energyDivider: 10,
  maturityMin: 0,
  maturityMax: 10000,
  maturityDivider: 100,
  statsMin: 0,
  statsMax: 100,
  statsLoveRequirement: 75,
  breedingMax: 2,
  penMaxSize: 6,
  creaturesMax: 250,
  itemsMax: 100,
  itemLifeMax: 500,

  firebase: {
    apiKey: 'AIzaSyBrq7lRwYNpFlGtce0PTTRvgz14JvS2WlQ',
    authDomain: 'play.llamabreedergame.com',
    projectId: 'llamabreeder',
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
