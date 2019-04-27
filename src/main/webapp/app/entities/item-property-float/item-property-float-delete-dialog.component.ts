import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPropertyFloat } from 'app/shared/model/item-property-float.model';
import { ItemPropertyFloatService } from './item-property-float.service';

@Component({
    selector: 'jhi-item-property-float-delete-dialog',
    templateUrl: './item-property-float-delete-dialog.component.html'
})
export class ItemPropertyFloatDeleteDialogComponent {
    itemPropertyFloat: IItemPropertyFloat;

    constructor(
        protected itemPropertyFloatService: ItemPropertyFloatService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPropertyFloatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemPropertyFloatListModification',
                content: 'Deleted an itemPropertyFloat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-property-float-delete-popup',
    template: ''
})
export class ItemPropertyFloatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyFloat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemPropertyFloatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemPropertyFloat = itemPropertyFloat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-property-float', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-property-float', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
