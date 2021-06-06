# Kick Kids

### Automatic deployment using Fastlane:

1. Install Java 8: https://adoptopenjdk.net/
2. Install `Ruby`:
   - Windows: https://rubyinstaller.org/downloads/ (`Ruby+Devkit 2.7.2-1 (x64)` is recommended)
   - Linux: https://www.ruby-lang.org/vi/documentation/installation/
   - macOS: built-in
3. Install `fastlane` with the following command:
   ```
   gem install fastlane –NV
   ```
4. Name key file as `.key.json` and put it into `fastlane` folder.
5. Put `.env.secret` file into `fastlane` folder.
6. For first time setup, go to directory where `fastlane` folder resides and run this command to fetch metadata:
   ```
   fastlane supply init
   ```
7. Open project in `Android Studio` and make sure that all combinations of configurations & flavors run without errors.
8. Run `fastlane deploy` and follow instructions to distribute app:
   - `Development`, `Production` is internal app sharing.
   - `Play Store` will deploy app to internal testing, which then can be promoted & published to production.
