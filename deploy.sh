#!/bin/bash --login

apkPath=./app/build/outputs/apk
apkDebugFile=${apkPath}/app-debug.apk
apkReleaseFile=${apkPath}/app-release.apk
propertiesFile=./app/packer.properties

function pkgDebug(){
    echo "package debug internal start"
    ./gradlew clean assembleDebug
    . ${propertiesFile};
    apkNew="debug-${version}.apk";
    mv ${apkDebugFile} ${apkNew};
    cp ${apkNew} /Users/tiansj/
    # scp ${apkNew} root@192.168.1.28:/var/download/mobile/phone/android
    echo "package debug internal success"
    rm -rf ./${apkNew}
}

function pkgRelease(){
    echo "package release outer start"
    ./gradlew clean assembleRelease
    . ${propertiesFile};
    apkNew="release-${version}.apk";
    mv ${apkReleaseFile} ${apkNew};
    cp ${apkNew} /Users/tiansj/
    # scp ${apkNew} root@192.168.1.28:/var/download/mobile/phone/android
    echo "package release outer success"
    rm -rf ./${apkNew}
}
echo "Build start.........."
    pkgDebug;
    pkgRelease;
echo "Build end"



