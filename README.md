# GitHub Organization Repository Browser

[![Build Status](http://img.shields.io/travis/gresrun/GHOrgBrowser.svg)](https://travis-ci.org/gresrun/GHOrgBrowser) [![Coverage Status](http://img.shields.io/coveralls/gresrun/GHOrgBrowser.svg)](https://coveralls.io/r/gresrun/GHOrgBrowser) [![License Apache 2.0](http://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/gresrun/ghorgbrowser/blob/master/LICENSE)

***

## How do I use it?

This project requires Java 7+. Download the latest source at:

	https://github.com/gresrun/GHOrgBrowser

Example usage:

```bash
mvn clean install && java -jar target/ghorgbrowser-1.0.0-SNAPSHOT.jar server config/development.yml
```

This will launch a Jetty web server on ports 8080 and 8081.
Visit <a href="http://localhost:8080/">http://localhost:8080/</a> to see the application in action.

***

## License

Copyright 2015 Greg Haines

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
