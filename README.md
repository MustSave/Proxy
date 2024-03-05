# Web Proxy

This program serves as a web proxy with the following features:

- **Session Cookie Management**: It stores session cookies and then overwrites cookie values to enable single sign-on for crawling across multiple devices or multiple processes.

- **Header Manipulation**: When using libraries such as `requests` or `axios`, it automatically overrides default header values to bypass bot detection.

- **Cloudflare and CAPTCHA Detection**: The program includes functionality to detect Cloudflare protection and CAPTCHA challenges. Future updates will also include features to bypass these protections.

