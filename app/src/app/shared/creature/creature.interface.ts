export interface Creature {
  id: string;
  name: string;
  generation: number;
  colors: Colors;
  sex: string;
  genes: Genes;
  statistics: Statistics;
  wild: boolean;
  pregnant: boolean;
}

export interface Colors {
  color1: Color;
  color2: Color;
}

export interface Color {
  code: string;
  name: string;
}

export interface Genes {
  gene1: string;
  gene2: string;
}

export interface Statistics {
  energy: number;
  love: number;
  thirst: number;
  hunger: number;
  maturity: number;
}


