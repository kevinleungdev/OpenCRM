import gql from "graphql-tag";

export const LOGIN_MUTATION = gql`
  mutation login($email: String!, $password: String) {
    login(loginInput: { email: $email, password: $password }) {
      accessToken
      refreshToken
    }
  }
`;

export const REGISTER_MUTATION = gql`
  mutation register($email: String!, $password: String!) {
    register(registerInput: { email: $email, password: $password }) {
      id
      email
    }
  }
`;

export const GET_IDENTITY_QUERY = gql`
  query Me {
    me {
      id
      name
      email
      phone
      jobTitle
      timezone
      avatarUrl
    }
  }
`;
