const MS_PER_DAY = 1000 * 60 * 60 * 24;

export const daysInBetween = (start, end) => {
  const a = new Date(start);
  const b = new Date(end);
  a.setHours(0, 0, 0, 0);
  b.setHours(0, 0, 0, 0);
  return Math.round((b - a) / MS_PER_DAY);
};
