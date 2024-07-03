use std::io::Read;

use anyhow::{anyhow, Context};

pub fn get_msgs(
    client: &reqwest::blocking::Client,
    get_msgs_query: &crate::model::GetMsgs,
) -> reqwest::Result<reqwest::blocking::Response> {
    client
        .get("http://localhost:8080/v1/msg")
        .query(get_msgs_query)
        .send()
}
pub fn get_msgs_with_ctx_err(
    client: &reqwest::blocking::Client,
    get_msgs_query: &crate::model::GetMsgs,
) -> anyhow::Result<Vec<crate::model::ChatMsg>> {
    (|| -> anyhow::Result<_> {
        let mut resp =
            get_msgs(client, get_msgs_query).context("Failed to connect with the server.")?;
        if resp.status().is_success() {
            match resp.json::<Vec<crate::model::ChatMsg>>() {
                Ok(messages) => Ok(messages),
                Err(err) => Err(anyhow!("Failed to deserialize server's response: {err}")),
            }
        } else {
            let mut resp_body = vec![0; 1024];
            let read_count = resp.read(&mut resp_body).unwrap_or_else(|err| {
                tracing::warn!("Request body-reading error was silenced: {err}");
                0
            });
            resp_body.truncate(read_count);
            let resp_body = String::from_utf8_lossy(&resp_body);
            Err(anyhow!("Server returned:\n{resp:?}\n\n{}...", resp_body))
        }
    })()
    .with_context(|| "Failed to get chat messages.")
}
pub fn post_msg(
    client: &reqwest::blocking::Client,
    post_msg_query: &crate::model::PostMsg,
) -> reqwest::Result<reqwest::blocking::Response> {
    client
        .post("http://localhost:8080/v1/msg")
        .query(post_msg_query)
        .send()
}
pub fn post_msg_with_ctx_err(
    client: &reqwest::blocking::Client,
    post_msg_query: &crate::model::PostMsg,
) -> anyhow::Result<crate::model::ChatMsg> {
    (|| -> anyhow::Result<_> {
        let mut resp =
            post_msg(client, post_msg_query).context("Failed to connect with the server.")?;
        if resp.status().is_success() {
            match resp.json::<crate::model::ChatMsg>() {
                Ok(message) => Ok(message),
                Err(err) => Err(anyhow!("Failed to deserialize server's response: {err}")),
            }
        } else {
            let mut resp_body = vec![0; 1024];
            let read_count = resp.read(&mut resp_body).unwrap_or_else(|err| {
                tracing::warn!("Request body-reading error was silenced: {err}");
                0
            });
            resp_body.truncate(read_count);
            let resp_body = String::from_utf8_lossy(&resp_body);
            Err(anyhow!("Server returned:\n{resp:?}\n\n{}...", resp_body))
        }
    })()
    .with_context(|| "Failed to post user-provided chat message.")
}
