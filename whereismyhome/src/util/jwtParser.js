export default function (token) {
  try {
    var Buffer = require('buffer/').Buffer
    return Buffer.from(token.split(".")[1], "base64").toString('utf-8');
  } catch (e) {
    return null;
  }
}
