# Partiu

Projeto da disciplina de Engenharia de Software, UFCG 2017.1

# Nota importante: backend

Para o backend, iremos utilizar o python 3, virtualenv, [Flask](http://flask.pocoo.org/) como framework
e [MongoDB](https://www.mongodb.com/) como o sistema para o banco de dados.

Para instalar o virtualenv

```
$ pip install virtualenv
```

Em seguida, no diretório do backend, digite

```
$ virtualenv -p python3 env
```

E para ativar o ambiente virtual (depende do sistema operacional)

```
$ # sistemas UNIX
$ source ./env/bin/activate
$ # Windows
$ .\env\bin\activate.bat # ou .ps1, caso utilize o powershell
```

Para instalar os pacotes necessários utilize o arquivo requirements.txt

```
$ pip install -r ./requirements.txt
```

*Note que se você for adicionar alguma dependência, atualize o requirements.txt!*