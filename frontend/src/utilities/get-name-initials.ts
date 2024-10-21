import { count } from "console";

export const getNameInitials = (name = "", count = 2) => {
  const initials = name
    .split(" ")
    .map((s) => s[0])
    .join("");

  const filtered = initials.replace(/[^a-zA-Z]/g, "");
  return filtered.slice(0, count).toUpperCase();
};
