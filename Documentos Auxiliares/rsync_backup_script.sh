#!/bin/bash

# Snapshots di�rios em diret�rios do tipo "daily-4-Thu", "daily-5-Fri", e assim por diante.
if [[ "$1" == "daily" ]]
then
	path=daily-`date +%u-%a`
fi
	
# Snapshots semanais em diret�rios do tipo "weekly-1", onde 1 � o dia do m�s
if [[ "$1" == "weekly" ]]
then
	path=weekly-`date +%d`
fi

# Snapshots mensais em diret�rios do tipo "monthly-04-Apr"
if [[ "$1" == "monthly" ]]
then
	path=monthly-`date +%m-%b`
fi


# Executa o script com o comando "go" como segundo par�metro para executar o rsync,
# caso contr�rio imprime o comando que teria sido executado.
# -a, --archive : archive (resumo de -rlptgoD, que usa recurs�o e preserva quase tudo)
# -v, --verbose : verbosity (mais informa��o nos logs)
# -z, -- compress : compress�o de dados
# --delete : remove os arquivos presentes no diret�rio de destino que n�o est�o presentes no diret�rio fonte
if [[ "$2" == "go" ]]
then
        rsync -avz --delete /home/nicholas/TCC /home/nicholas/Dropbox/TCC_backups/$path
else
        echo rsync -avz --delete /home/nicholas/TCC /home/nicholas/Dropbox/TCC_backups/$path
fi

