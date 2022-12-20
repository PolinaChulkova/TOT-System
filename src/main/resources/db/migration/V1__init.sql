CREATE TABLE IF NOT EXISTS public.securities
(
    id            bigserial,
    emitent_title character varying(255),
    name          character varying(255),
    reg_number    character varying(255),
    sec_id        character varying(255),
    CONSTRAINT securities_pkey PRIMARY KEY (id),
    CONSTRAINT uk_sec_id_constraint UNIQUE (sec_id)
    );

CREATE TABLE IF NOT EXISTS public.trading_histories
(
    sec_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    tarde_date date NOT NULL,
    num_trades bigint,
    open double precision,
    CONSTRAINT trading_histories_pkey PRIMARY KEY (sec_id, tarde_date),
    CONSTRAINT fk_trading_histories_security FOREIGN KEY (sec_id)
    REFERENCES public.securities (sec_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )
