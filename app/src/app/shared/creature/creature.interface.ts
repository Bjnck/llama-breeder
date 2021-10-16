export interface Creature {
  id: string;
  name: string;
  generation: number;
  colors: Colors;
  sex: string;
  genes: Genes;
  statistics: Statistics;
  wild: boolean;
  pregnancyCount: number;
  pregnant: boolean;
  pregnancyStartTime: string;
  pregnancyEndTime: string;
  pregnancyMale: Parent;
  parents: Parents;
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

export interface Parents {
  parent1: Parent;
  parent2: Parent;
}

export interface Parent {
  sex: string;
  colors: Colors;
  genes: Genes;
}


