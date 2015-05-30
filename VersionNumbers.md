# Introduction #
Ochan is an open source project. It aims to ALWAYS be a working product when built from source; but, project consumers will want to grab a pre-built runnable binary version and go. However, when maintaining an actual production install.. knowing what the impact of an upgrade is going to be quickly... is pretty important.


# Details #

Ochan maintains 3 digit version numbers while in general development. Their digits, from left to right, represent:
  1. Milestone
  1. API Compatibility Level
  1. Heartbeat

Heartbeats happen on a whim. It's a way for new features and issues to be released quickly for feedback. On occasion, API Compatibility is broken between releases. This means entire stacks that have been deployed, code against the web services, and databases & current data isn't guaranteed to be in a working state after upgrading.

Milestone Releases are full blown release ready builds. Milestone to Milestone, there are no API compatibility guarantees. Milestones represent the 'best foot forward' releases of ochan.

Ideally, this will remain uncomplicated... heartbeats will happen fairly regularly, API changes will get figured out fairly quickly and stabilize, and only two or three major milestones will be created before the project is stable.

# Examples #
| 0.0.1 | The first heartbeat |
|:------|:--------------------|
| 0.1.4 | The 4th heartbeat of the 1st API level |
| 1.0.0 | The first milestone |
| 1.1.0 | An API adjustment to the last milestone |
| 1.3.12 | 12 heartbeats, 3 api adjusts, beyond the first milestone |