export interface Pen {
  id: string;
  size: number;
  creatures: PenContent[];
  items: PenContent[];
}

export interface PenContent {
  id: string;
}

export interface PenInfo {
  count: number;
  sizes: Price[];
}

export interface Price {
  count: number;
  price: number;
}
