Learn Haskell 
=============

## Install Haskell (using stack)

Haskell can be installed via [Stack](https://docs.haskellstack.org/en/stable/README/).

1. First install stack itself by running: -

```bash
    curl -sSL https://get.haskellstack.org/ | sh
```

1. And then run GHCI using `stack ghci`.

More details on stack can be found [here](Stack.md)

## To use Gollum to view/edit the markdown documentation

### Build the docker image

```
docker build -t gollum .
```

### Start the container

```
docker run -v `pwd`:/wiki -p 4567:80 gollum
```

### View the Documentation
Go to `http://127.0.0.1:4567`
