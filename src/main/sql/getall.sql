use hip_concerts;

SELECT e.ensemble_id, ensemble_name, p.performer_id, performer_name,
i.instrument_id, instrument_name
FROM perf_instr_ensembles_concerts as a
JOIN ensembles as e on e.ensemble_id = a.ensemble_id
JOIN performers as p on p.performer_id = a.performer_id
JOIN instruments as i on i.instrument_id = a.instrument_id
WHERE a.ensemble_id=2 ORDER BY performer_name ASC;