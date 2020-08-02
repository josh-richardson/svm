# svm [![Build Status](https://travis-ci.org/josh-richardson/svm.svg?branch=master)](https://travis-ci.org/josh-richardson/svm)

Solidity Version Manager inspired by nvm & jabba. Written in Kotlin & Java, but available with no dependencies as a native image thanks to GraalVM.

Allows for you to easily install & switch between different versions of the Solidity compiler (solc) from the Terminal. Supports Linux and macOS, and Windows. Unlike other version managers for Solidity, svm has no dependencies, and is able to download native images to run directly on on all supported platforms, without any extra compilation required.

### Installation:
Linux & macOS:
```bash
curl -L https://github.com/josh-richardson/svm/raw/master/install.sh | bash && source ~/.svm/svm.sh
```

Windows:
```
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-Expression (
  Invoke-WebRequest https://github.com/josh-richardson/svm/raw/master/install.ps1 -UseBasicParsing
).Content
```

Note that on Windows, Java 11 is required as GraalVM cannot compile complex native images for Windows targets. The Powershell execution policy must also be set at such a level as to allow the powershell profile to execute successfully, and svm will not work in a vanilla command prompt (cmd.exe).

### Executables
Executables used by svm are downloaded using TLS, and are served directly from the Solidity Github [releases page](https://github.com/ethereum/solidity/releases) for Windows and Linux. Unfortunately native images are not supplied for macOS, so they are downloaded from [this repository](https://github.com/web3j/solidity-darwin-binaries/releases).

### Usage:
#### Demo:
![svm in use webm](https://i.imgur.com/TcQpSlu.gif)

#### Available commands:
```
Solidity Version Manager (https://github.com/josh-richardson/svm).

Usage:
  svm [command]

Available Commands:
  install <version>         Download and install Solidity
  uninstall <version>       Uninstall Solidity
  use <version>             Modify PATH to use specific Solidity version
  current                   Display currently 'use'ed version
  ls                        List installed versions
  ls-remote                 List remote versions available for install
  alias <name> <version>    Resolve or update an alias
  unalias <name>            Delete an alias
  deactivate                Deactivates svm in the current shell
```

#### .svmrc support

You may find it useful to be able to use a specific version of solc for each project, and to not have to remember each time. To do this, in a given project directory execute `echo $version > .svmrc`, replacing `$version` with the version you want to use for the current project. Then, in future, execute `svm use` in the same directory to use the specified Solidity version. 

To automatically run `svm use` wherever a `.svmrc` file exists, see [this StackOverflow question](https://stackoverflow.com/questions/23556330/run-nvm-use-automatically-every-time-theres-a-nvmrc-file-on-the-directory).
