export default function (token) {
  try {
    return Buffer.from(token.split(".")[1], "base64");
  } catch (e) {
    return null;
  }
}
