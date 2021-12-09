export class NavigationService {
  private static running = false;

  static isRunning(): boolean {
    return this.running;
  }

  static toggleRunning() {
    this.running = !this.running;
  }
}
