package pedro.almeida.financialcontrol.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedro.almeida.financialcontrol.application.dtos.response.TransactionCategoryResponseDTO;
import pedro.almeida.financialcontrol.domain.models.TransactionCategory;
import pedro.almeida.financialcontrol.domain.repositories.TransactionCategories;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTransactionCategoriesTest {

    @Mock
    private TransactionCategories transactionCategoryRepository;
    @InjectMocks
    private FindAllTransactionCategories findAllTransactionCategories;
    private final List<TransactionCategory> categories = List.of(
            new TransactionCategory(UUID.randomUUID(), "Category 1", ""),
            new TransactionCategory(UUID.randomUUID(), "Category 2", "")
    );

    @Test
    void executeShouldReturnAllCategories() {
        when(transactionCategoryRepository.findAll()).thenReturn(categories);
        List<TransactionCategoryResponseDTO> expected = categories.stream().map(TransactionCategoryResponseDTO::new).toList();

        List<TransactionCategoryResponseDTO> categoriesDTO = findAllTransactionCategories.execute();

        assertEquals(expected, categoriesDTO);
        verify(transactionCategoryRepository).findAll();
    }
}