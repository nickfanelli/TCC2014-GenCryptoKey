#!/bin/bash

# Snapshots diários em diretórios do tipo "daily-4-Thu", "daily-5-Fri", e assim por diante.
if [[ "$1" == "daily" ]]
then
	path=daily-`date +%u-%a`
fi
	
# Snapshots semanais em diretórios do tipo "weekly-1", onde 1 é o dia do mês
if [[ "$1" == "weekly" ]]
then
	path=weekly-`date +%d`
fi

# Snapshots mensais em diretórios do tipo "monthly-04-Apr"
if [[ "$1" == "monthly" ]]
then
	path=monthly-`date +%m-%b`
fi


# Executa o script com o comando "go" como segundo parâmetro para executar o rsync,
# caso contrário imprime o comando que teria sido executado.
# -a, --archive : archive (resumo de -rlptgoD, que usa recursão e preserva quase tudo)
# -v, --verbose : verbosity (mais informação nos logs)
# -z, -- compress : compressão de dados
# --delete : remove os arquivos presentes no diretório de destino que não estão presentes no diretório fonte
if [[ "$2" == "go" ]]
then
        rsync -avz --delete /home/nicholas/TCC /home/nicholas/Dropbox/TCC_backups/$path
else
        echo rsync -avz --delete /home/nicholas/TCC /home/nicholas/Dropbox/TCC_backups/$path
fi

