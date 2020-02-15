#!/usr/bin/env bash
time curl http://127.0.0.1:8080/run -X POST --data '
{
  "action_name": "action_name",
  "action_version": "0.0.1",
  "activation_id": "1a665cb914a540b9a65cb914a510b9ec",
  "api_key": "1a665cb914a540b9a65cb914a510b9ec",
  "deadline": "1578809961244",
  "namespace": "1a665cb914a540b9a65cb914a510b9ec",
  "transaction_id": "105c2c123a9a1943046c83e025e9dbe2",
  "value": {
    "HUMIO_SERVER_TOKEN": "HUMIO_SERVER_TOKEN",
    "__ow_headers": {
      "accept": "*/*",
      "accept-encoding": "gzip",
      "cdn-loop": "cloudflare",
      "cf-connecting-ip": "cf-connecting-ip",
      "cf-ipcountry": "US",
      "cf-ray": "cf-ray",
      "cf-visitor": "{\"scheme\":\"https\"}",
      "host": "us-south.functions.cloud.ibm.com",
      "user-agent": "curl/7.54.0",
      "x-forwarded-for": "x-forwarded-for",
      "x-forwarded-host": "us-south.functions.cloud.ibm.com",
      "x-forwarded-port": "443",
      "x-forwarded-proto": "https",
      "x-global-k8fdic-transaction-id": "x-global-k8fdic-transaction-id",
      "x-real-ip": "162.158.74.147",
      "x-request-id": "x-request-id"
    },
    "__ow_method": "post",
    "__ow_path": "/api/welcome",
    "__ow_query": "ok=1&not=0"
  }
}
'