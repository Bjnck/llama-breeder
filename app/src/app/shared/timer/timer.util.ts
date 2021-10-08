export class TimerUtil {
  public static utc(date: Date): Date {
    const utc = Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
      date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());

    return new Date(utc);
  }

  public static timeLeft(date: Date): number {
    return this.utc(date).getTime() - this.utc(new Date()).getTime();
  }
}
