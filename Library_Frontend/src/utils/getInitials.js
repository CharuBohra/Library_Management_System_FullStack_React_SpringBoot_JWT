export const getInitials = (fullName) => {
  return fullName
    .trim()
    .split(/\s+/)
    .map((name) => name[0])
    .slice(0, 2)
    .join("")
    .toUpperCase();
};
