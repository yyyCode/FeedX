import http from './http';

export const login = (account, password) => {
  const data = new URLSearchParams();
  data.append('account', account);
  data.append('password', password);
  return http.post('/auth/login', data);
};

