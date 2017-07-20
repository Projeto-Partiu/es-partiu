sig Usuario {
	seguidores: set Usuario
}

sig Comentarios {
	autor: one Usuario
}


sig Evento {
	criador: one Usuario,
	interessados : set Usuario,
	confirmados: set Usuario,
	comentarios : set Comentarios
}

fact CriadorEventoInteressado {
	all e:Evento | e.criador in e.interessados
}

//usuario ou marca presença ou interesse
fact UsuarioInteressadoOuConfirmado {
	all e:Evento | no u:Usuario | u in e.interessados and u in e.confirmados
} 	

//nao faz sentido o usuario se seguir
fact UmUsuarioNaoSeSegue {
	all u:Usuario | u not in u.seguidores
}

//cada comentario so pertence a um evento
fact ComentariosSoUmEvento {
	all c:Comentarios | one e:Evento | c in e.comentarios
}

pred show[] {}

run show for 4
