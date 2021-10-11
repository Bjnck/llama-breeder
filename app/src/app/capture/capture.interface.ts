export interface Capture {
  id: string;

  startTime: string;
  endTime: string;

  quality: number;
  bait: number;

  sex: string;
  color: Color;
  gene: string;
}

export interface Color {
  code: string;
  name: string;
}
