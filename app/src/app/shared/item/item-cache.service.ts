import {Injectable} from '@angular/core';

@Injectable()
export class ItemCacheService {
  private static totalElements;
  private static filterElements;

  static getTotalElements(): number {
    return this.totalElements;
  }

  static setTotalElements(elements: number) {
    if (!this.totalElements) {
      this.totalElements = elements;
    }
  }

  static incrementTotalElements() {
    if (this.totalElements) {
      this.totalElements++;
    }
  }

  static decrementTotalElements() {
    if (this.totalElements) {
      this.totalElements--;
    }
  }

  static getFilterElements(): number {
    return this.filterElements;
  }

  static setFilterElements(elements: number) {
    this.filterElements = elements;
  }
}
