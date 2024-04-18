# SystemBarColorist
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg)](https://android-arsenal.com/api?level=19)
[![license](https://img.shields.io/github/license/balazsgerlei/SystemBarColorist)](https://creativecommons.org/publicdomain/zero/1.0/)
[![last commit](https://img.shields.io/github/last-commit/balazsgerlei/SystemBarColorist?color=018786)](https://github.com/balazsgerlei/SystemBarColorist/commits/main)
[![](https://jitpack.io/v/balazsgerlei/SystemBarColorist.svg)](https://jitpack.io/#balazsgerlei/SystemBarColorist)

Set the color of Android status and navigation bars while also automatically applying light or dark font color.

## Setup

The library is currently hosted on `jitpack.io`. You can add it as a depenency to your project:

Step 1. Add the JitPack repository to your build file

Add it in your root `build.gradle` at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.balazsgerlei:SystemBarColorist:1.0.0-alpha02'
	}



