Horten Documentation
====================

The Horten documentation is written in Jekyll with the following theme features:

- Gulp
- SASS
- Sweet Scroll
- Particle.js
- BrowserSync
- Font Awesome and Devicon icons
- Google Analytics
- Info Customization

Basic Setup
-----------

1. [Install Jekyll](http://jekyllrb.com)
2. Clone the particle theme: `git clone git@github.com:QubitPi/Horten.git`
3. `cd Horten/docs`
4. Edit `_config.yml` to edit the site metadata.

Color and Particle Customization
--------------------------------

- Color Customization

  - Edit the sass variables

- Particle Customization

  - Edit the json data in particle function in app.js
  - Refer to [Particle.js](https://github.com/VincentGarreau/particles.js/) for help

Running the Site Locally
------------------------

In order to compile the assets and run Jekyll on local we need to follow those steps:

- Install [NodeJS](https://nodejs.org/)
- Install [Jekyll](https://jekyllrb.com): `sudo gem install bundler jekyll`
- Install [Yarn](https://yarnpkg.com/): `npm install -g yarn`
- Install dependencies: `yarn`
- Run: `gulp`
