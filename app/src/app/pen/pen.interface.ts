export interface Pen {
  id: string;
  size: number;
  creatures: PenContent[];
  items: PenContent[];
}

export interface PenContent {
  id: string;
}
